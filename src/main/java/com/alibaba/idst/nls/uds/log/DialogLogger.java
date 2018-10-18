package com.alibaba.idst.nls.uds.log;

import com.alibaba.idst.nls.uds.context.DialogSession;
import org.apache.logging.log4j.Logger;

public interface DialogLogger {

    @Deprecated
    Logger getLogger(DialogSession session);

    void debug(String message);

    void warn(String message);

    void info(String message);

    void error(String message);

}
