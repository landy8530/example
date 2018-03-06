package com.yaml.setting;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class FailedToResolveConfigExceptionTest {

    @Test
    public void testInit() {
        FailedToResolveConfigException failedToResolveConfigException = new FailedToResolveConfigException();
        Assert.assertTrue(failedToResolveConfigException instanceof RuntimeException);
        failedToResolveConfigException = new FailedToResolveConfigException("Can't load config file success");
        Assert.assertEquals("Can't load config file success", failedToResolveConfigException.getMessage());
        IOException ioException = new IOException();
        failedToResolveConfigException = new FailedToResolveConfigException("Can't load config file success",ioException);
        Assert.assertEquals(ioException, failedToResolveConfigException.getCause());
        failedToResolveConfigException = new FailedToResolveConfigException(ioException);
        Assert.assertEquals(ioException, failedToResolveConfigException.getCause());
        failedToResolveConfigException = new FailedToResolveConfigException("Can't load config file success",ioException, true, true);
        Assert.assertEquals(ioException, failedToResolveConfigException.getCause());
    }
}
