package com.salesmanager.shop.laapp.impl;

/**
 * @author Jack Ying
 * @version 1.0
 * @date 1/26/23 11:00 AM
 */
import java.util.Set;
import javax.inject.Inject;
import javax.servlet.ServletOutputStream;


;
import com.salesmanager.core.business.services.catalog.product.PricingService;
import com.salesmanager.core.business.services.order.OrderService;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.Order;
import com.salesmanager.core.model.order.orderproduct.OrderProduct;
import com.salesmanager.shop.laapp.ExcelService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
public class ExcelServiceImpl implements ExcelService {


    @Inject
    private PricingService pricingService;

    @Inject
    private OrderService orderService;

    @Override
    public void export(Long id, MerchantStore store, ServletOutputStream out) throws Exception{
        try{

            Order order = orderService.getOrder(id, store);

            if(order.getMerchant().getId().intValue()!=store.getId().intValue()) {
                throw new Exception("Invalid order");
            }

            // 第一步，创建一个workbook，对应一个Excel文件
            HSSFWorkbook workbook = new HSSFWorkbook();

            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
            HSSFSheet hssfSheet = workbook.createSheet("hoja1");

            // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short

            int countRow = 0;

            // 第四步，创建单元格，并设置值表头 设置表头居中
            HSSFCellStyle hssfCellStyle = workbook.createCellStyle();

            //居中样式
            //hssfCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            HSSFCell hssfCell = null;
            HSSFRow row = hssfSheet.createRow(countRow);
            row.createCell(0).setCellValue("No. de Pedido:");
            String orderNo = order.getId().toString();
            row.createCell(1).setCellValue(orderNo);
            countRow++;
            row = hssfSheet.createRow(countRow);
            row.createCell(0).setCellValue("Nombre de Cliente:");
            String fname = order.getBilling().getFirstName();
            String lname = order.getBilling().getLastName();
            row.createCell(1).setCellValue(fname + " " + lname);
            countRow++;
            row = hssfSheet.createRow(countRow);
            row.createCell(0).setCellValue("Email de Cliente:");
            String email = order.getCustomerEmailAddress();
            row.createCell(1).setCellValue(email);
            countRow++;
            row = hssfSheet.createRow(countRow);
            row.createCell(0).setCellValue("Fecha:");
            String DOP = order.getDatePurchased().toString();
            row.createCell(1).setCellValue(DOP);
            countRow++;
            row = hssfSheet.createRow(countRow);
            row.createCell(0).setCellValue("Estado:");
            String state = order.getStatus().toString();
            row.createCell(1).setCellValue(state);
            countRow++;
            hssfSheet.createRow(countRow);

            Set<OrderProduct> products = order.getOrderProducts();

            countRow++;
            row = hssfSheet.createRow(countRow);
            row.createCell(0).setCellValue("Codigo");
            row.createCell(1).setCellValue("Descripción");
            row.createCell(2).setCellValue("Cantidad");
            row.createCell(3).setCellValue("Precio Neto");

            for (OrderProduct product : products) {
                countRow++;
                row = hssfSheet.createRow(countRow);
                row.createCell(0).setCellValue(product.getSku());
                row.createCell(1).setCellValue(product.getProductName());
                row.createCell(2).setCellValue(product.getProductQuantity());

                row.createCell(3).setCellValue(pricingService.getDisplayAmount(product.getOneTimeCharge(), store));

            }

            countRow++;
            hssfSheet.createRow(countRow);

            /*
            // 第五步，写入实体数据
            Person  person1=new Person("1","张三","123","26");
            Person  person2=new Person("2","李四","123","18");
            Person  person3=new Person("3","王五","123","77");
            Person  person4=new Person("4","徐小筱","123","1");

            //这里我把list当做数据库啦
            ArrayList<Person>  list=new ArrayList<Person>();
            list.add(person1);
            list.add(person2);
            list.add(person3);
            list.add(person4);

            for (int i = 0; i < list.size(); i++) {
                row = hssfSheet.createRow(i + countRow + 1);
                Person person = list.get(i);

                // 第六步，创建单元格，并设置值
                String  pid = null;
                if(person.getId() != null){
                    pid = person.getId();
                }
                row.createCell(0).setCellValue(pid);
                String name = "";
                if(person.getName() != null){
                    name = person.getName();
                }
                row.createCell(1).setCellValue(name);
                String password = "";
                if(person.getPassword() != null){
                    password = person.getPassword();
                }
                row.createCell(2).setCellValue(password);
                String age=null;
                if(person.getAge() !=null){
                    age = person.getAge();
                }
                row.createCell(3).setCellValue(age);
            }

             */

            // 第七步，将文件输出到客户端浏览器
            try {
                workbook.write(out);
                out.flush();
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }catch(Exception e){
            e.printStackTrace();
            throw new Exception("Export failed！");

        }
    }
}