package com.redescooter.ses.tool.other;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 20/12/2019 11:05 上午
 * @ClassName: Test
 * @Function: TODO
 */
public class Test {

    /**
     * 生成规则设备编号:设备类型+五位编号（从1开始，不够前补0）
     *
     * @param equipmentType
     *              设备类型
     * @param equipmentNo
     *              最新设备编号
     * @return
     */
    public static String getNewEquipmentNo(String equipmentType, String equipmentNo){
        String newEquipmentNo = "00001";

        if(equipmentNo != null && !equipmentNo.isEmpty()){
            int newEquipment = Integer.parseInt(equipmentNo) + 1;
            newEquipmentNo = String.format(equipmentType + "%05d", newEquipment);
        }

        return newEquipmentNo;
    }

    public static void main(String[] args) {

        String equipmentNo = Test.getNewEquipmentNo("SYXH", "00032");
        System.out.println("生成设备编号：" + equipmentNo);
    }


}
