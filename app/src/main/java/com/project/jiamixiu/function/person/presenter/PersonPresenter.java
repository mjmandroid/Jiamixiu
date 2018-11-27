package com.project.jiamixiu.function.person.presenter;

import com.project.jiamixiu.function.person.inter.IPersonView;
import com.project.jiamixiu.function.person.request.PersonRequest;
import com.project.jiamixiu.manger.HttpManager;

import java.util.HashMap;

public class PersonPresenter   {
    IPersonView personView;

    public void loadPersonInfo(){
        HashMap<String,String> map = PersonRequest.getPersonInfoRequest();
//        HttpManager.sendRequest();
    }
}
