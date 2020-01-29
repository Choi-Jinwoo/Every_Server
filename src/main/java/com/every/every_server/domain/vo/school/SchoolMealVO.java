package com.every.every_server.domain.vo.school;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

/**
 * 학교 급식 정보 VO
 */
@Getter
@AllArgsConstructor
public class SchoolMealVO {
    @JsonProperty("meal_code")
    private int mealCode; // 조식/중식/석식

    @JsonProperty("meal_date")
    private String mealDate; // 급식 일자

    @JsonProperty("meal_name")
    private String mealName; // 급식 내용

    public SchoolMealVO(JsonObject jsonMeal) {
        try {
            this.mealCode = jsonMeal.get("MMEAL_SC_CODE").getAsInt();
            this.mealDate = jsonMeal.get("MLSV_YMD").getAsString();
            this.mealName = jsonMeal.get("DDISH_NM").getAsString();
        } catch (NullPointerException e) {
            throw e;
        }
    }
}
