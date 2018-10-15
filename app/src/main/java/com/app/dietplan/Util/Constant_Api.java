package com.app.dietplan.Util;

import com.app.dietplan.BuildConfig;
import com.app.dietplan.Item.AboutUsList;
import com.app.dietplan.Item.SubCategoryList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 07-09-2017.
 */

public class Constant_Api {

    //main server api url
    public static String url = BuildConfig.My_api;

    //Image url
    public static String image = url + "images/";

    //App info url
    public static String app_info = url + "api.php?app_details";

    //Category
    public static String category = url + "api.php?cat_list";

    //Sub Category
    public static String sub_category = url + "api.php?cat_id=";

    //Sub Category
    public static String notification = url + "api.php?diet_id=";

    public static int AD_COUNT = 0;
    public static int AD_COUNT_SHOW = 0;

    public static AboutUsList aboutUsList;
    public static List<SubCategoryList> subCategoryLists = new ArrayList<>();
    public static List<SubCategoryList> notificationSCL = new ArrayList<>();

}
