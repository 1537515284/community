package com.ls.community.service;

import com.ls.community.mapper.AdMapper;
import com.ls.community.model.Ad;
import com.ls.community.model.AdExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdService {
    @Autowired
    private AdMapper adMapper;

    public List<Ad> list(String pos){
        AdExample adExample = new AdExample();
        adExample.createCriteria()
                .andStatusEqualTo(1)
                .andPosEqualTo(pos)
                .andGmtStartLessThan(System.currentTimeMillis())
                .andGmtEndGreaterThan(System.currentTimeMillis());
        return adMapper.selectByExample(adExample);
    }
}
