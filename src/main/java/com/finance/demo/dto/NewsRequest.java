package com.finance.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsRequest {

    private String resultType;
    private List<String> conceptUri;
    private String conceptOper;
    private String categoryUri;
    private int eventsCount;
    private String eventsSortBy;
    private int forceMaxDataTimeWindow;
    private boolean includeEventTitle;
    private boolean includeEventSummary;
    private String apiKey;
}