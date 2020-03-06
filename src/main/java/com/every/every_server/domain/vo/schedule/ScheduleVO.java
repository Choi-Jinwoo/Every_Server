package com.every.every_server.domain.vo.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 일정 VO
 */
@Getter
@Setter
@NoArgsConstructor
public class ScheduleVO {
    private Integer idx;
    private String title;
    private String content;
    @JsonProperty("start_date")
    private String startDate;
    @JsonProperty("end_date")
    private String endDate;
}
