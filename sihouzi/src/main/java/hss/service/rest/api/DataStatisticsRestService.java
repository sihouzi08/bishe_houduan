package hss.service.rest.api;

import com.foreveross.springboot.dubbo.utils.Payload;

/**
 * Created by Foreveross on 2017/1/11.
 */
public interface DataStatisticsRestService {

    public Payload getSum(String type,String fields,String table);


}
