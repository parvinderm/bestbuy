package com.bestbuy.productinfo;

import com.bestbuy.productsinfo.ProductsSteps;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class ProductCURDTestWithSteps extends TestBase {
    static String name = "amazon-battery" + TestUtils.getRandomValue();
    static String type = "HardGood" + TestUtils.getRandomValue();

    static String upc = "09876545" + TestUtils.getRandomValue();
    static String description = "pack of 4" + TestUtils.getRandomValue();
    static String manufacturer = "amazon" + TestUtils.getRandomValue();
    static String model = "EDRT-5" + TestUtils.getRandomValue();
    static String url = "www.amazi.com" + TestUtils.getRandomValue();
    static String image = "http/imgd" + TestUtils.getRandomValue();


    static int productsId;

    @Steps
    ProductsSteps productSteps;

    @Title("This will create a new product")
    @Test
    public void test001() {
        ValidatableResponse response=productSteps.createProducts(name,type,upc,description,manufacturer,model,url,image);
        response.statusCode(201);
    }

    @Title("verify if product is created")
    @Test
    public void test002() {
        HashMap<String, Object> productsMapData = productSteps.getProductsInfoByName(name);
        Assert.assertThat(productsMapData, hasValue(name));
        productsId = (int) productsMapData.get("id");
        System.out.println(productsId);

    }
    @Title("update the product information")
    @Test
    public void test003() {
        name = name + "radhu";

        productSteps.updateProducts(productsId,name);
        HashMap<String, Object> productsMapData = productSteps.getProductsInfoByName(name);
        Assert.assertThat(productsMapData, hasValue(name));
    }

    @Title("Delete product information by productsID and verify its deleted")
    @Test
    public void test004(){
        productSteps.deleteProductsInfoByProductsId(productsId).statusCode(200);
        productSteps.getProductsInfoByProductsId(productsId).statusCode(404);
    }



    }
