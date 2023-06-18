package com.salesmanager.shop.laapp;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.Order;

import javax.servlet.ServletOutputStream;

/**
 * @author Jack Ying
 * @version 1.0
 * @date 1/27/23 2:21 PM
 */
public interface ExcelService {
    void export(Long id, MerchantStore store, ServletOutputStream out) throws Exception;
}
