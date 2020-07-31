package com.redescooter.ses.web.ros.constant;

/**
 * @ClassName:MondayParameterName
 * @description: MondayParameterName
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/31 13:41
 */
public interface MondayParameterName {
    //板子参数
    String BOARD_PARAMETER="#{BOARD_PARAMETER}";

    //条目参数
    String CREATE_ITEM_NAME="#{CREATE_ITEM_NAME}";

    //分组参数
    String CREATE_BELONG_GROUP="#{CREATE_BELONG_GROUP}";

    //每一行列模板参数
    String CREATE_COLUMN_VALUES="#{CREATE_COLUMN_VALUES}";

    //权限
    String AUTHORIZATION ="authorization";

    //板子名称
    String BOARD_NAME="#{BOARD_NAME}";

    //板子类型
    String BOARD_KIND="#{BOARD_KIND}";

    //板子工作空间
    String BOARD_WORDSPACE="#{BOARD_WORDSPACE}";

    //板子应用的模板
    String BOARD_TEMPLETE="#{BOARD_TEMPLETE}";

    //创建分组时指定的板子Id
    String GROUP_BOARD_ID="#{GROUP_BOARD_ID}";

    //板子下指定分组名称
    String GROUP_BOARD_NAME="#{GROUP_BOARD_NAME}";

    String COLUMN_BOARD_ID="#{COLUMN_BOARD_ID}";

    //列名
    String COLUMN_TITLE="#{COLUMN_TITLE}";

    //列数据类型
    String COLUMN_TYPE="#{COLUMN_TYPE}";

    //列默认值
    String COLUMN_DEFAULTS="#{COLUMN_DEFAULTS}";

}
