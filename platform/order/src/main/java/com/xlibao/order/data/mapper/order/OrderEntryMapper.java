package com.xlibao.order.data.mapper.order;

import com.xlibao.metadata.order.OrderEntry;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderEntryMapper {

    int createOrderEntry(OrderEntry orderEntry);

    OrderEntry getOrderEntry(@Param("orderId") long orderId);

    OrderEntry getOrderEntryForSequenceNumber(@Param("orderSequenceNumber") String orderSequenceNumber);

    List<OrderEntry> getOrderEntrys(@Param("sequenceNumber") String sequenceNumber);

    List<OrderEntry> showConsumerOrders(@Param("partnerId") String partnerId, @Param("partnerUserId") String partnerUserId, @Param("target") byte target, @Param("orderStatusSet") String orderStatusSet, @Param("type") int type, @Param("pageStartIndex") int pageStartIndex, @Param("pageSize") int pageSize);

    List<OrderEntry> showMerchantOrders(@Param("partnerId") String partnerId, @Param("shippingPassportId") long shippingPassportId, @Param("orderStatusSet") String orderStatusSet, @Param("type") int type, @Param("pageStartIndex") int pageStartIndex, @Param("pageSize") int pageSize);

    List<OrderEntry> showCourierOrders(@Param("partnerId") String partnerId, @Param("courierPassportId") long courierPassportId, @Param("orderStatusSet") String orderStatusSet, @Param("type") int type, @Param("pageStartIndex") int pageStartIndex, @Param("pageSize") int pageSize);

    List<OrderEntry> searchOrders(@Param("partnerId") String partnerId, @Param("passportId") long passportId, @Param("searchKeyValue") String searchKeyValue, @Param("type") int type, @Param("roleType") int roleType,
                                  @Param("pageStartIndex") int pageStartIndex, @Param("pageSize") int pageSize);

    int findInvalidOrderSize(@Param("partnerId") String partnerId, @Param("orderType") int orderType, @Param("matchStatus") int matchStatus, @Param("timeout") String timeout);

    List<OrderEntry> findInvalidOrders(@Param("partnerId") String partnerId, @Param("orderType") int orderType, @Param("matchStatus") int matchStatus, @Param("timeout") String timeout);

    int paymentOrder(@Param("orderId") long orderId, @Param("status") int status, @Param("matchBeforeStatus") int matchBeforeStatus, @Param("transType") String transType, @Param("daySortNumber") int daySortNumber);

    int acceptOrder(@Param("orderId") long orderId, @Param("courierPassportId") long courierPassportId, @Param("courierNickName") String courierNickName, @Param("courierPhone") String courierPhone, @Param("status") int status, @Param("matchBeforeStatus") int matchBeforeStatus);

    int updateOrderStatus(@Param("orderId") long orderId, @Param("status") int status, @Param("matchBeforeStatus") int matchBeforeStatus, @Param("deliverStatus") int deliverStatus, @Param("beforeDeliverStatus") int beforeDeliverStatus);

    int correctOrderPrice(@Param("orderId") long orderId, @Param("actualPrice") long actualPrice, @Param("totalPrice") long totalPrice, @Param("discountPrice") long discountPrice,
                          @Param("distributionFee") long distributionFee, @Param("deliverType") int deliverType);

    int fillDailyRowSort(@Param("orderId") long orderId, @Param("daySortNumber") int daySortNumber);

    int refreshRefundStatus(@Param("orderId") long orderId, @Param("refundStatus") int refundStatus);

    int batchResetOverdueOrderStatus(@Param("partnerId") String partnerId, @Param("orderType") int orderType, @Param("matchStatus") int matchStatus, @Param("expectStatus") int expectStatus, @Param("timeout") String timeout);
}