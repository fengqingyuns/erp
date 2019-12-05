package com.hanyun.scm.api.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.hanyun.ground.util.date.DateCalcUtil;
import com.hanyun.ground.util.http.HttpClient;
import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.IdGenerateType;
import com.hanyun.scm.api.dao.IdGenerateSeqDao;
import com.hanyun.scm.api.domain.IdGenerateSeq;
import com.hanyun.scm.api.domain.result.CodeResult;
import com.hanyun.scm.api.domain.result.DateResult;
import com.hanyun.scm.api.service.IdGenerateSeqService;
import com.hanyun.scm.api.utils.PropertiesUtil;
import com.hanyun.scm.api.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * <pre>
 *                             _ooOoo_
 *                            o8888888o
 *                            88" . "88
 *                            (| -_- |)
 *                            O\  =  /O
 *                         ____/`---'\____
 *                       .'  \\|     |//  `.
 *                      /  \\|||  :  |||//  \
 *                     /  _||||| -:- |||||-  \
 *                     |   | \\\  -  /// |   |
 *                     | \_|  ''\---/''  |   |
 *                     \  .-\__  `-`  ___/-. /
 *                   ___`. .'  /--.--\  `. . __
 *                ."" '<  `.___\_<|>_/___.'  >'"".
 *               | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *               \  \ `-.   \_ __\ /__ _/   .-` /  /
 *          ======`-.____`-.___\_____/___.-`____.-'======
 *                             `=---='
 *          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *
 *                     佛祖保佑        永无BUG
 *
 *          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * </pre>
 * IdGenerateServiceImpl
 * Date: 2016/12/7
 * Time: 下午6:27
 *
 * @author tianye@hanyun.com
 */
@Service
public class IdGenerateSeqServiceImpl implements IdGenerateSeqService {

    @Resource
    private IdGenerateSeqDao idGenerateSeqDao;

    @Override
    public String generateId(IdGenerateType idGenerateType) {
        DateResult result;
        String tableName;
        String code = null;
        switch (idGenerateType) {
            case GL:
                tableName = "id_generate_supplier_type_seq";
                break;
            case GY:
                code = getDocumentId("164bf3aa-9d21-4d46-bc6b-465e8da849ff", "OTHER_CODE");
                tableName = "id_generate_supplier_seq";
                break;
            case CG:
                code = getDocumentId("1b7a727e-6229-49b8-8968-160ec00bf350", "INVOICE_CODE");
                tableName = "id_generate_purchase_apply_seq";
                break;
            case CJ:
                code = getDocumentId("00cc3e71-0518-4873-af8c-3f0f666336f8", "INVOICE_CODE");
                tableName = "id_generate_purchase_plan_seq";
                break;
            case CD:
                code = getDocumentId("7e92b964-f833-4c42-901d-e6f1bf6b6978", "INVOICE_CODE");
                tableName = "id_generate_purchase_order_seq";
                break;
            case CR:
                code = getDocumentId("71e7b8a2-8a68-4e89-8354-a1a6e12ccf11", "INVOICE_CODE");
                tableName = "id_generate_purchase_stock_in_seq";
                break;
            case CT:
                code = getDocumentId("c915ba2e-e04c-45cb-a01a-954581088260", "INVOICE_CODE");
                tableName = "id_generate_purchase_return_seq";
                break;
            case RK:
                code = getDocumentId("a92f6dce-d53b-4cc0-8a91-5330ead6efef", "INVOICE_CODE");
                tableName = "id_generate_stock_in_seq";
                break;
            case CK:
                code = getDocumentId("a92f6dce-d53b-4cc0-8a91-5330ead6efef", "INVOICE_CODE");
                tableName = "id_generate_stock_out_seq";
                break;
            case DB:
                tableName = "id_generate_stock_transfer_seq";
                break;
            case SH:
                tableName = "id_generate_stock_receiving_seq";
                break;
            case PR:
                code = getDocumentId("fe36fada-2880-4d16-8dc7-398dcaba2cb7", "INVOICE_CODE");
                tableName = "id_generate_stock_check_task_seq";
                break;
            case PD:
                code = getDocumentId("5d31f15c-2476-4ab2-aede-4c86fa5c0b58", "INVOICE_CODE");
                tableName = "id_generate_stock_check_order_seq";
                break;
            case PC:
                code = getDocumentId("c13e72bc-85c9-4be0-b8d7-c447570ddf00", "INVOICE_CODE");
                tableName = "id_generate_stock_check_difference_seq";
                break;
            case YK:
                code = getDocumentId("98b7a788-6b48-4aed-b830-cace7f482617", "INVOICE_CODE");
                tableName = "id_generate_stock_interval_seq";
                break;
            case CZD:
                code = getDocumentId("6f7c8116-b4ca-431f-bdf0-eaea6ce2b402", "INVOICE_CODE");
                tableName = "id_generate_disassembly_seq";
                break;
            case BS:
                code = getDocumentId("40c20a03-e858-46bf-81f6-5341faa867b6", "INVOICE_CODE");
                tableName = "id_generate_spill_loss_seq";
                break;
            case BY:
                code = getDocumentId("40c20a03-e858-46bf-81f6-5341faa867b6", "INVOICE_CODE");
                tableName = "id_generate_spill_loss_seq";
                break;
            case BHSQ:
                code = getDocumentId("6f7c8116-b4ca-431f-bdf0-eaea6ce2b413", "INVOICE_CODE");
                tableName = "id_generate_stock_transfer_seq";
                break;
            case PSD:
                code = getDocumentId("66a30ec4-6858-4955-a612-c277b65dbaa2", "INVOICE_CODE");
                tableName = "id_generate_disassembly_seq";
                break;
            case WLFH:
                code = getDocumentId("798413e7489f4993b1473532c5e16000", "INVOICE_CODE");
                tableName = "id_generate_disassembly_seq";
                break;
            case YSRK:
                code = getDocumentId("bf3a64c1-d30e-4c04-98c5-2c7d6bd9eab9", "INVOICE_CODE");
                tableName = "id_generate_disassembly_seq";
                break;
            case HJ:
                code = getDocumentId("b670ef67-8424-49d3-afec-b2770797679b", "OTHER_CODE");
                tableName = "";
                break;
            default:
                tableName = "";
        }
        if (!StringUtils.isEmpty(code)) {
            return code;
        }
        if (StringUtils.isEmpty(tableName)) {
            throw new RuntimeException("分类不存在,生成编号失败!");
        }
        result = idGenerateSeqDao.getTime(tableName);
        IdGenerateSeq idGenerateSeq = new IdGenerateSeq();
        idGenerateSeq.setTableName(tableName);
        if (result != null && result.getLast() != null && result.getNow() != null) {
            idGenerateSeq.setCreateTime(result.getNow());
        }

        if (result != null && result.getLast() != null && result.getNow() != null
                && result.getLast().before(DateCalcUtil.getDayBegin(result.getNow()))) {
            idGenerateSeqDao.truncate(tableName);
        }
        idGenerateSeqDao.generateSeq(idGenerateSeq);
        if (idGenerateSeq == null || idGenerateSeq.getId() <= 0) {
            throw new RuntimeException("生成" + idGenerateType + "编号失败!");
        }
        return StringUtil.generateDocumentId(idGenerateType.toString(), idGenerateSeq.getId());
    }

    private String getDocumentId(String codeId, String codeType) {
        try {
            PropertiesUtil propertiesUtil = new PropertiesUtil();
            Properties properties = propertiesUtil.getProperties("erp-api.properties");
            String idUrl = properties.getProperty("idUrl") + "/" + codeId + "/GET";
            String result = HttpClient.get(idUrl).addParameter("codeType", codeType).action().getBody();
            HttpResponse httpResponse = JsonUtil.fromJson(result, HttpResponse.class);
            CodeResult codeResult = JsonUtil.fromJson(JsonUtil.toJson(httpResponse.getData()), CodeResult.class);
            return codeResult.getCode();
        } catch (Exception e) {
            return null;
        }
    }
}
