package com.ZioSet.Service;

import com.ZioSet.model.CPUDetials;
import com.ZioSet.model.OSDetials;

public interface NotificationService {

	void sendNotificationForCPUChange(CPUDetials cpuDetials, CPUDetials cPU);

	void sendNotificationForOS(String string, OSDetials osDetials, OSDetials osDetials2);

}
