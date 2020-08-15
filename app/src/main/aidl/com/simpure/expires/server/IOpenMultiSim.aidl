package com.simpure.expires.server;

import com.simpure.expires.server.model.IOpenMultiSimCallback;

interface IOpenMultiSim {

    void registerCallback(in IOpenMultiSimCallback callback);

    void unRegisterCallback(in IOpenMultiSimCallback callback);

    void getAttachedDeviceMultiSimInfo();

    void downloadEsimProfile(String activationCode);
}
