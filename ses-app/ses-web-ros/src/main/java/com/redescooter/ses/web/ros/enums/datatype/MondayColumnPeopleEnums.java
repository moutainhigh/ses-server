package com.redescooter.ses.web.ros.enums.datatype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:MondayColumnPeopleEnums
 * @description: MondayColumnPeopleEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/31 13:56
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum MondayColumnPeopleEnums {

    /**
     * {
     *   "personsAndTeams": [
     *     {
     *       "id": 4616627,
     *       "kind": "person"
     *     },
     *     {
     *       "id": 4616666,
     *       "kind": "person"
     *     },
     *     {
     *       "id": 51166,
     *       "kind": "team"
     *     }
     *   ]
     * }
     */

    PERSONSANDTEAMS("personsAndTeams","个人或者团队"),
    ID("id","id"),
    KIND("kind","种类"),
    ;
    private String title;

    private String message;
}
