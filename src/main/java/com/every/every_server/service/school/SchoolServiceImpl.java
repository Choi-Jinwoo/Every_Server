package com.every.every_server.service.school;

import com.every.every_server.domain.entity.Member;
import com.every.every_server.domain.entity.Student;
import com.every.every_server.domain.repository.MemberRepo;
import com.every.every_server.domain.repository.StudentRepo;
import com.every.every_server.domain.vo.school.SchoolMealVO;
import com.every.every_server.domain.vo.school.SchoolVO;
import com.every.every_server.lib.ApiRequest;
import com.every.every_server.lib.Url;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SchoolServiceImpl implements SchoolService{
    @Value("${neis.key}")
    private String neisKey;
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private MemberRepo memberRepo;

    /**
     * 학교 조회(SchoolId) API
     * @return 학교
     */
    @Override
    public SchoolVO getSchoolBySchoolId(String reqSchoolId) throws Exception {
        Url apiURL = new Url("https://open.neis.go.kr/hub/schoolInfo");
        // Query 추가
        apiURL.addQuery("KEY", neisKey);
        apiURL.addQuery("SD_SCHUL_CODE", reqSchoolId);
        apiURL.addQuery("Type", "json");

        try {
            JsonObject object = ApiRequest.getRequest(apiURL);
            JsonArray schoolInfo = (JsonArray) object.get("schoolInfo");
            JsonObject rowObj = (JsonObject) schoolInfo.get(1);
            JsonArray row = (JsonArray) rowObj.get("row");
            JsonObject jsonSchool = (JsonObject) row.get(0);
            SchoolVO school = new SchoolVO(jsonSchool);

            return school;
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "없는 학교.");
        }  catch (Exception e) {
            throw e;
        }
    }

    /**
     * 학교 목록 조회 API
     * @return 학교 리스트
     */
    @Override
    public List<SchoolVO> getSchoolList(String reqSchoolName) throws Exception {
        Url apiURL = new Url("https://open.neis.go.kr/hub/schoolInfo");

        // Query 추가
        apiURL.addQuery("KEY", neisKey);
        // 한글 인코딩 후 추가
        apiURL.addQuery("SCHUL_NM", URLEncoder.encode(reqSchoolName, "UTF-8"));
        apiURL.addQuery("Type", "json");
        apiURL.addQuery("pSize", "1000");
        System.out.println(apiURL.getBase());
        try {
            JsonObject object =ApiRequest.getRequest(apiURL);
            JsonArray schoolInfo = (JsonArray) object.get("schoolInfo");

            JsonObject rowObj = (JsonObject) schoolInfo.get(1);
            JsonArray row = (JsonArray) rowObj.get("row");
            List<SchoolVO> schoolList = new ArrayList<>();
            for (int i = 0; i < row.size(); i++) {
                JsonObject jsonSchool = (JsonObject) row.get(i);
                SchoolVO school = new SchoolVO(jsonSchool);
                schoolList.add(school);
            }
            return schoolList;
        } catch (NullPointerException e) {
            List<SchoolVO> schoolList = new ArrayList<>();
            return schoolList;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 학교 급식 조회 API
     * @return 학교 급식 리스트
     */
    @Override
    public List<SchoolMealVO> getSchoolMeals(Integer memberIdx) throws Exception {
        Optional<Member> member = memberRepo.findById(memberIdx);

        // 회원이 존재하지 않을 경우
        if (!member.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "존재하지 않는 회원.");
        }

        Student student = studentRepo.findByMember(member.get());

        // 학생이 존재하지 않을 경우
        if (student == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "존재하지 않는 학생.");
        }

        String schoolId = student.getSchoolId();
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            Calendar cal = Calendar.getInstance();
            String dateFormatYYYYMM = dateFormat.format(cal.getTime());

            // URL 생성
            SchoolVO school = getSchoolBySchoolId(schoolId);
            Url apiURL = new Url("https://open.neis.go.kr/hub/mealServiceDietInfo");
            apiURL.addQuery("KEY", neisKey);
            apiURL.addQuery("SD_SCHUL_CODE", school.getSchoolId());
            apiURL.addQuery("ATPT_OFCDC_SC_CODE", school.getOfficeId());
            apiURL.addQuery("MLSV_YMD", dateFormatYYYYMM);
            apiURL.addQuery("Type", "json");

            // JSON Parsing
            JsonObject object =ApiRequest.getRequest(apiURL);
            JsonArray schoolInfo = (JsonArray) object.get("mealServiceDietInfo");
            JsonObject rowObj = (JsonObject) schoolInfo.get(1);
            JsonArray row = (JsonArray) rowObj.get("row");
            List<SchoolMealVO> mealList = new ArrayList<>();

            for (int i = 0; i < row.size(); i++) {
                JsonObject jsonMeal = (JsonObject) row.get(i);
                SchoolMealVO meal = new SchoolMealVO(jsonMeal);
                mealList.add(meal);
            }
            return mealList;
        } catch (NullPointerException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "해당 급식 정보 없음.");
        } catch (Exception e) {
            throw e;
        }
    }
}
