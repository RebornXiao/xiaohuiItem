package com.xlibao.payment.service.trans;

import com.xlibao.common.constant.payment.TransStatusEnum;
import com.xlibao.payment.data.model.PaymentTransactionLogger;
import com.xlibao.payment.listener.TransactionEventListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chinahuangxc on 2017/2/3.
 */
@Component
public class TransactionEventListenerManager {

    private Map<String, TransactionEventListener> transactionEventListenerMap = new HashMap<>();

    void registerTransactionEventListener(String moduleName, TransactionEventListener transactionEventListener) {
        transactionEventListenerMap.put(moduleName, transactionEventListener);
    }

    public void notifyFinishPaymented(PaymentTransactionLogger transactionLogger, TransStatusEnum transStatusEnum, boolean callback) {
        for (TransactionEventListener transactionEventListener : transactionEventListenerMap.values()) {
            transactionEventListener.notifyFinishPaymented(transactionLogger, transStatusEnum, callback);
        }
    }
}