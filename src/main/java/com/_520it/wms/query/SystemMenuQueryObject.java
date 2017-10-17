package com._520it.wms.query;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by 阎神 on 2017/9/17.
 */

@Setter@Getter@ToString
public class SystemMenuQueryObject extends QueryObject {

    private Long parentId = -1L;

    private String parentSn;

}
