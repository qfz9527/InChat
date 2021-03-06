package com.myself.unclecatmyself.auto;

import com.myself.unclecatmyself.common.properties.InitNetty;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Create by UncleCatMySelf in 2018/12/06
 **/
@Configuration
@ConditionalOnClass
@EnableConfigurationProperties({InitNetty.class})
public class ServerAutoConfigure {

    private static  final  int _BLACKLOG =   1024;

    private static final  int  CPU =Runtime.getRuntime().availableProcessors();

    private static final  int  SEDU_DAY =10;

    private static final  int TIMEOUT =120;

    private static final  int BUF_SIZE=10*1024*1024;


    public ServerAutoConfigure(){

    }

//    @Bean
//    @ConditionalOnMissingBean(name = "sacnScheduled")
//    public ScanRunnable initRunable(@Autowired InitNetty serverBean){
//        long time =(serverBean==null || serverBean.getPeriod()<5)?10:serverBean.getPeriod();
//        ScanRunnable sacnScheduled = new SacnScheduled(time);
//        Thread scanRunnable = new Thread(sacnScheduled);
//        scanRunnable.setDaemon(true);
//        scanRunnable.start();
//        return sacnScheduled;
//    }


    @Bean(initMethod = "open", destroyMethod = "close")
    @ConditionalOnMissingBean
    public InitServer initServer(InitNetty serverBean){
        if(!ObjectUtils.allNotNull(serverBean.getWebport(),serverBean.getServerName())){
            throw  new NullPointerException("not set port");
        }
        if(serverBean.getBacklog()<1){
            serverBean.setBacklog(_BLACKLOG);
        }
        if(serverBean.getBossThread()<1){
            serverBean.setBossThread(CPU);
        }
        if(serverBean.getInitalDelay()<0){
            serverBean.setInitalDelay(SEDU_DAY);
        }
        if(serverBean.getPeriod()<1){
            serverBean.setPeriod(SEDU_DAY);
        }
        if(serverBean.getHeart()<1){
            serverBean.setHeart(TIMEOUT);
        }
        if(serverBean.getRevbuf()<1){
            serverBean.setRevbuf(BUF_SIZE);
        }
        if(serverBean.getWorkerThread()<1){
            serverBean.setWorkerThread(CPU*2);
        }
        return new InitServer(serverBean);
    }

}
