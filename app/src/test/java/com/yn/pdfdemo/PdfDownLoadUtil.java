package com.yn.pdfdemo;


import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author daoren
 * @version 3.0
 * @description:
 * @date 2021/5/25 15:16
 */
public class PdfDownLoadUtil {

    private SimpleDateFormat sdfDateTIme = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//    private FileProperties fileProperties;

    private static final String bloodPdfUrl = "pdf/blood.pdf";
    private static final String d2PdfUrl = "pdf/d2.pdf";


    /**
     * 生产pdf模板
     *
     * @throws Exception
     */
    public String createAllPdf(BloodReportReq req) throws Exception {
        //填充创建pdf
        PdfReader reader = null;
        PdfStamper stamp = null;
        String pdfTemplate = null;
        boolean isD2 = false;
        try {
            if (!StringUtil.isEmpty(req.getD2())) {
                reader = new PdfReader("E:\\androidDemo01\\PDFDemo\\app\\src\\test\\assets\\d2.pdf");
                isD2 = true;
            } else {
                reader = new PdfReader("E:\\androidDemo01\\PDFDemo\\app\\src\\test\\assets\\blood.pdf");
            }

            // 上传文件保存在当天日期命名的文件夹下
            Date date = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
            String today = ft.format(date);
            File path = new File("E:\\androidDemo01\\PDFDemo\\pdf\\");

            File parent = new File(path, today);


            if (!parent.exists()) {
                parent.mkdirs();
            }
            int length = 0;
            String number = "";
            boolean isNewNumber = true;
            File[] files = parent.listFiles();
            for (File file : files) {
                String name = file.getName();
                if (name.lastIndexOf(req.getPhone()) > 0) {
                    isNewNumber = false;
                    number = name.substring(name.lastIndexOf("-") + 1, name.lastIndexOf("."));
                }
                if (isNewNumber && name.lastIndexOf(".pdf") > 0) {
                    length += 1;
                }
            }
            String numberParent = today.replace("/", "");
            StringBuffer numberSub = new StringBuffer();
            if (isNewNumber) {
                if (length == 0) {
                    numberSub.append("0001");
                } else {
                    for (int i = 1000; i > 1; i /= 10) {
                        if (length < i) {
                            numberSub.insert(0, "0");
                        }
                    }
                    numberSub.append(length);
                }
                number = numberParent + numberSub.toString();
            }

            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String formattedDate = formatter.format(curDate);

            String filename = new StringBuffer()
                    .append(req.getName())
                    .append("-")
                    .append(req.getPhone())
                    .append("-")
                    .append(formattedDate) + ".pdf";

            File newPath = new File(parent, filename);


            stamp = new PdfStamper(reader, new FileOutputStream(newPath));

            //取出报表模板中的所有字段
            AcroFields form = stamp.getAcroFields();
            // 填充数据
            form.setField("name", req.getName());
            form.setField("sex", req.getSex());
            form.setField("age", req.getAge());
            form.setField("number", number);
            form.setField("phone", req.getPhone());
            if (isD2) {
                form.setField("d2", req.getD2());
            } else {
                form.setField("apptt", req.getApptt());
                form.setField("fib", req.getFib());
                form.setField("pt", req.getPt());
                form.setField("inr", req.getInr());
                form.setField("tt", req.getTt());
                form.setField("act", req.getAct());
            }


            //报告生成日期
            Date nowTime = new Date();
            String generationdate = sdfDateTIme.format(nowTime);
            form.setField("createTime", generationdate);
            //随机前7-8分钟
            int max = 480, min = 420;
            int ran = (int) (Math.random() * (max - min) + min);
            form.setField("takeTime", sdfDateTIme.format(TimeUtils.getDownSecondDate(ran, nowTime)));
            max *= 2;
            min = min * 2 - 60;
            int ran2 = (int) (Math.random() * (max - min) + min);
            form.setField("gatherTime", sdfDateTIme.format(TimeUtils.getDownSecondDate(ran2, nowTime)));
            stamp.setFormFlattening(true);
            return today + "/" + filename;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stamp != null) {
                stamp.close();
            }
            if (reader != null) {
                reader.close();
            }
        }
        return "pdf生产失败！";
    }
}
