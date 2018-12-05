package com.quantdo.market.service.ws;

import io.netty.channel.ChannelHandler;

/**
 * 封装handleres
 * <br>创建日期：2018年4月21日 下午4:57:08 <br>
 * <b>Copyright 2015 上海量投网络科技有限公司 All Rights Reserved</b>
 * 
 * @author 苏宏斌
 * @since 1.0
 * @version 1.0
 */
public interface ChannelHandlerHolder {

    ChannelHandler[] handlers();
}
