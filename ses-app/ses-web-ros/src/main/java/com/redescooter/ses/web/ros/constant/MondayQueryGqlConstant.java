package com.redescooter.ses.web.ros.constant;

/**
 * @ClassName:MondayQueryConstant
 * @description: MondayQueryConstant
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/09 16:44
 */
public interface MondayQueryGqlConstant {

    String BOARD_PARAMETER="#{BOARD_PARAMETER}";

    String CREATE_ITEM_NAME="#{CREATE_ITEM_NAME}";

    String CREATE_BELONG_GROUP="#{CREATE_BELONG_GROUP}";

    String CREATE_COLUMN_VALUES="#{CREATE_COLUMN_VALUES}";

    String QUERY_BOARD="query {\n" +
            "\tboards (limit:100){\n" +
            "\t\tid name board_folder_id board_kind communication description\n" +
            "\t}\n" +
            "}";

    String QUERY_GROUP="query {\n" +
            "\tboards(ids: "+BOARD_PARAMETER+") {\n" +
            "\t\tgroups {\n" +
            "\t\t\tid archived color position deleted title\n" +
            "\t\t}\n" +
            "\n" +
            "\t}\n" +
            "}";

    String QUERY_TAGS="query {\n" +
            "\ttags {\n" +
            "\t\tname color id\n" +
            "\t}\n" +
            "}\n";

    String QUERY_COLUMN="query {\n" +
            "\tboards (ids: "+BOARD_PARAMETER+") {\n" +
            "\t\tcolumns {\n" +
            "\t\t\tid title type archived pos settings_str width\n" +
            "\t\t}\n" +
            "\t}\n" +
            "}";

    String MUTATION_DATA="mutation {\n" +
            "\tcreate_item (board_id: "+BOARD_PARAMETER+", group_id: \""+CREATE_BELONG_GROUP+"\", item_name: \""+CREATE_ITEM_NAME+"\",column_values:\""+ CREATE_COLUMN_VALUES+"\") {\n" +
            "\t\tid\n" +
            "\t}\n" +
            "}";
}
