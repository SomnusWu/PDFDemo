package com.yn.pdfdemo

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 *
 * 1：准备工作
 * 运行createaFDF 即可根据pdf模板生成 对应的pdf
 *
 */
class ExampleUnitTest {

    @Test
    fun createFDF(){

        val mPdfDownLoadUtil = PdfDownLoadUtil()

        val mBloodReportReq = BloodReportReq()
        mBloodReportReq.name = "张三"
        mBloodReportReq.phone = "1514587515"
        //本地项目有2个pdf模板，这里区分是哪个模板
        mBloodReportReq.d2 = "2"

        mPdfDownLoadUtil.createAllPdf(mBloodReportReq)
    }
}