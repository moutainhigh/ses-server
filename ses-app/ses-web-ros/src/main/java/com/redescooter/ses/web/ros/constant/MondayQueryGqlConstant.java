package com.redescooter.ses.web.ros.constant;

/**
 * @ClassName:MondayQueryConstant
 * @description: MondayQueryConstant
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/09 16:44
 */
public interface MondayQueryGqlConstant {

    //查询板子
    String QUERY_BOARD = "query {\n" +
            "\tboards (limit:100){\n" +
            "\t\tid name board_folder_id board_kind communication description\n" +
            "\t}\n" +
            "}";
    
    //板子插入
    String MUTATION_BOARD="";

    //查询分组
    String QUERY_GROUP = "query {\n" +
            "\tboards(ids: " + MondayParameterName.BOARD_PARAMETER + ") {\n" +
            "\t\tgroups {\n" +
            "\t\t\tid archived color position deleted title\n" +
            "\t\t}\n" +
            "\n" +
            "\t}\n" +
            "}";

    String MUTATION_GROUP="";

    //查询标签
    String QUERY_TAGS = "query {\n" +
            "\ttags {\n" +
            "\t\tname color id\n" +
            "\t}\n" +
            "}\n";

    //查询分组模板
    String QUERY_COLUMN = "query {\n" +
            "\tboards (ids: " + MondayParameterName.BOARD_PARAMETER + ") {\n" +
            "\t\tcolumns {\n" +
            "\t\t\tid title type archived pos settings_str width\n" +
            "\t\t}\n" +
            "\t}\n" +
            "}";

    //模板数据插入
    String MUTATION_ITEM_COLUMN_DATA = "mutation {\n" +
            "\tcreate_item (board_id: " + MondayParameterName.BOARD_PARAMETER + ", group_id: \"" + MondayParameterName.CREATE_BELONG_GROUP + "\", item_name: \"" + MondayParameterName.CREATE_ITEM_NAME + "\"," +
            "column_values:\"" + MondayParameterName.CREATE_COLUMN_VALUES +
            "\") {\n" +
            "\t\tid\n" +
            "\t}\n" +
            "}";
}
