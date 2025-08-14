package com.zj.api;


import com.zj.api.dto.LockMarketPayOrderRequestDTO;
import com.zj.api.response.LockMarketPayOrderResponseDTO;
import com.zj.api.response.Response;

/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 * @description 营销交易服务接口
 * @create 2025-01-11 13:49
 */
public interface IMarketTradeService {

    Response<LockMarketPayOrderResponseDTO> lockMarketPayOrder(LockMarketPayOrderRequestDTO lockMarketPayOrderRequestDTO);

}