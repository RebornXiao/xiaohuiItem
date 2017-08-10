/**
 * 小程序配置文件
 */

var host = "192.168.1.102:8080/market"

var config = {

    // 下面的地址配合云端 Server 工作
    host,

    //商品类型接口
    itemTypes: {
        url : "http://192.168.1.102:8080/market/item/openapi/itemTypes.do",
        data : { marketId : 100000 }
    },
    // 找到商店
    findMarket: {
        url : "http://192.168.1.102:8080/market/market/findMarket.do",
        data : { passportId : 100000, marketId : 0 }
    },
    // 展示可用的商店
    showMarkets: {
        url : "http://192.168.1.102:8080/market/market/showMarkets.do",
        data : { passportId : 100000 , longitude : 0, latitude : 0}
    }
};

module.exports = config

