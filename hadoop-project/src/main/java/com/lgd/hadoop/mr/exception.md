
java.lang.UnsatisfiedLinkError: org.apache.hadoop.io.nativeio.NativeIO$Windows.access0(Ljava/lang/String;I)Z

修改org.apache.hadoop.io.nativeio.NativeIO源码为：
public static boolean access(String path, AccessRight desiredAccess)
        throws IOException {
    return true;
}

Exceptionin thread "main" java.lang.UnsatisfiedLinkError:
org.apache.hadoop.util.NativeCrc32.nativeComputeChunkedSumsByteArray(II[BI[BIILjava/lang/String;JZ)V

这是由于hadoop.dll、winutils.exe版本问题，2.4之前的和之后的需要的不一样。
需要选择正确的版本并且在Hadoop/bin和C：\windows\system32上将其替换。

