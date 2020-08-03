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
    String MUTATION_BOARD = "mutation {\n" +
            "\tcreate_board (board_name: " + MondayParameterName.BOARD_NAME + ", board_kind: " + MondayParameterName.BOARD_KIND + ", workspace_id:" + MondayParameterName.BOARD_WORDSPACE +
            ", template_id:" + MondayParameterName.BOARD_TEMPLETE + ") {\n" +
            "\t\tid\n" +
            "\t}\n" +
            "}\n";

    //查询分组
    String QUERY_GROUP = "query {\n" +
            "\tboards(ids: " + MondayParameterName.BOARD_PARAMETER + ") {\n" +
            "\t\tgroups {\n" +
            "\t\t\tid archived color position deleted title\n" +
            "\t\t}\n" +
            "\n" +
            "\t}\n" +
            "}";

    //创建分组
    String MUTATION_GROUP = "mutation {\n" +
            "create_group (board_id: " + MondayParameterName.GROUP_BOARD_ID + ", group_name: " + MondayParameterName.GROUP_BOARD_NAME + ") {\n" +
            "id\n" +
            "}\n" +
            "}";

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

    //插入列
    String MUTATION_COLUMN = "mutation {\n" +
            "create_column (board_id: " + MondayParameterName.COLUMN_BOARD_ID + ", title: " + MondayParameterName.COLUMN_TITLE + ", column_type: " + MondayParameterName.COLUMN_TYPE + ",defaults:" + MondayParameterName.COLUMN_DEFAULTS +
            ") " +
            "{\n" +
            "id\n" +
            "}\n" +
            "}";

    //创建一个webhook
    String MUTATION_CREATE_WEBHOOK = "mutation {\n" +
            "    create_webhook ( board_id: " + MondayParameterName.BOARD_PARAMETER + ", url: " + MondayParameterName.WEBHOOK_URL + ", event: " + MondayParameterName.WEBHOOK_EVENT + ",config: " +
            "" + MondayParameterName.WEBHOOK_CONFIG + " ) {\n" +
            "        id\n" +
            "                board_id\n" +
            "    }\n" +
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
