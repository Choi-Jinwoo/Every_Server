package com.every.every_server.service.school;

import com.every.every_server.domain.vo.school.SchoolMealVO;
import com.every.every_server.domain.vo.school.SchoolVO;
import com.every.every_server.lib.Url;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService{
    @Value("${neis.key}")
    private String neisKey;

    /**
     * 학교 목록 조회 API
     * @return 학교 리스트
     */
    @Override
    public List<SchoolVO> getSchoolList(String reqSchoolName) throws Exception {
        Url apiURL = new Url("https://open.neis.go.kr/hub/schoolInfo");
        ModelMapper modelMapper = new ModelMapper();

        try {
            // Query 추가
            apiURL.addQuery("KEY", neisKey);
            // 한글 인코딩
            apiURL.addQuery("SCHUL_NM", URLEncoder.encode(reqSchoolName, "UTF-8"));
            apiURL.addQuery("Type", "json");
            apiURL.addQuery("pSize", "1000");

            URL url = new URL(apiURL.getBase());
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");

            StringBuilder sb = new StringBuilder();
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();

                // Json Parsing
                JsonParser jsonParser = new JsonParser();

                JsonObject object = (JsonObject) jsonParser.parse(sb.toString());
                JsonArray schoolInfo = (JsonArray) object.get("schoolInfo");

                JsonObject rowObj = (JsonObject) schoolInfo.get(1);
                JsonArray row = (JsonArray) rowObj.get("row");
                List<SchoolVO> schoolList = new ArrayList<>();
                for (int i = 0; i < row.size(); i++) {
                    JsonObject jsonSchool = (JsonObject) row.get(i);
                    String schoolId = jsonSchool.get("SD_SCHUL_CODE").getAsString();
                    String officeId = jsonSchool.get("ATPT_OFCDC_SC_CODE").getAsString();
                    String schoolName = jsonSchool.get("SCHUL_NM").getAsString();
                    String schoolLocation = jsonSchool.get("LCTN_SC_NM").getAsString();

                    SchoolVO school = new SchoolVO(schoolId, officeId, schoolName, schoolLocation);
                    schoolList.add(school);
                }

                return schoolList;
            } else {
                throw new Exception();
            }
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
    public List<SchoolMealVO> getSchoolMeals(Integer idx) {
        return null;
    }
}
