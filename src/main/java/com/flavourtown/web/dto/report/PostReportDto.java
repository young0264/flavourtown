package com.flavourtown.web.dto.report;

import com.flavourtown.domain.report.ReportCategory;
import lombok.Data;

@Data
public class PostReportDto {
    ReportCategory reportCategory;
    String content;

}
