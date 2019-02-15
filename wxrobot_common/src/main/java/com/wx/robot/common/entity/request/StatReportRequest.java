package com.wx.robot.common.entity.request;

import com.wx.robot.common.entity.shared.StatReport;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatReportRequest {

    private BaseRequest BaseRequest;

    private Integer Count;

    private StatReport[] List;
}
