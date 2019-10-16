package com.github.xuchengen.web.unionpay;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.github.xuchengen.DefaultUnionPayClient;
import com.github.xuchengen.request.UnionPayFileTransferRequest;
import com.github.xuchengen.response.UnionPayFileTransferResponse;
import com.github.xuchengen.web.BaseCtl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

/**
 * 银联-在线网关支付文件传输接口
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/10/15
 */
@Controller
@RequestMapping(value = "/unionPay/onlineGatewayPay/fileTransfer")
public class UnionPayFileTransferCtl extends BaseCtl {

    private static final Log log = LogFactory.get(UnionPayFileTransferCtl.class);

    @GetMapping(value = {"", "/"})
    public String index() {
        return "/unionpay/onlineGateway/fileTransfer.html";
    }

    @PostMapping(value = "doDownload")
    @ResponseBody
    public ResponseEntity<byte[]> doDownload(@RequestParam String targetDate) {
        try {
            DefaultUnionPayClient client = getUnionPayClient();

            UnionPayFileTransferRequest unionPayFileTransferRequest = new UnionPayFileTransferRequest();
            unionPayFileTransferRequest.setSettleDate(targetDate);
            unionPayFileTransferRequest.setTxnType("76");
            unionPayFileTransferRequest.setTxnSubType("01");
            unionPayFileTransferRequest.setAccessType("0");
            unionPayFileTransferRequest.setFileType("00");

            UnionPayFileTransferResponse response = client.execute(unionPayFileTransferRequest);

            if (response.isOk()) {
                String fileContent = response.getFileContent();

                String fileName = response.getFileName();

                byte[] bytes = ZipUtil.unZlib(Base64.decode(fileContent));

                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentDispositionFormData("attachment", new String(StrUtil.bytes(fileName, "UTF-8"), StandardCharsets.ISO_8859_1));
                httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                return new ResponseEntity<>(bytes,
                        httpHeaders, HttpStatus.CREATED);
            } else {
                throw new RuntimeException("调用银联在线网关支付文件传输接口失败");
            }
        } catch (Exception e) {
            log.error("调用银联在线网关支付文件传输接口异常：{}", JSONUtil.toJsonStr(e));
            throw new RuntimeException("调用银联在线网关支付文件传输接口异常", e);
        }
    }
}
