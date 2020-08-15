package com.simpure.expires.server.model;

import com.simpure.expires.server.model.MultiSimDeviceInfo;

interface IOpenMultiSimCallback {

    void getDeviceMultiSimInfo(in MultiSimDeviceInfo deviceInfo);

    void getProfileDownloadStatus(int status);
}
