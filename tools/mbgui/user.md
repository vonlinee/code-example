





\#if (! $foo) 判断$foo为空，判断非空为 **#if ($foo)**

[Velocity中如何判断null - 麦田HH - 博客园 (cnblogs.com)](https://www.cnblogs.com/maijin/articles/4646375.html)





使用null工具判断

  \#if($null.isNull($foo))

注意这种方式特别有用，尤其你在需要这个判断作为一个判断字句时，比如我要你判断一个集合为null或为空时只能使用这种方式了：

  $if ($null.isNull($mycoll) || $mycoll.size()==0)


Vue-CodeMirror使用
https://blog.csdn.net/lfcss/article/details/106125784







