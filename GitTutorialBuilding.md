#How this tutorial was built#

Essentially you can use Github flavored Markdown and some (limited) HTML code. More info on this can be found at: https://help.github.com/articles/github-flavored-markdown/


<h2>Relative Links</h2>
The real key to making a self-sustaining tutorial that can be forked, merged, etc. is to make it completely self referencing.
To do so you must learn how to use links. Here are the most common uses:
```Markdown
[some text](link)  <- Basic Structure
[some text](file.md) <- Points to a file that is in the same folder as this file
[some text](../file.md) <- Points to a file that is in the parent folder of this folder where this file is currently at.
                        (Think of it as if you did cd .. and then accesed the file)
[some text](https://www.link.com) <- Points to a website
[some text](#file) <- Points to a header (or element) inside this file
    This point to something like: <h2 id="file">Some Link</h2>
```

*Hint:* To make a header a link
```Markdown
1. ##[text](link)##
```
<h2>Images</h2>
To show something *Raw* simply use:
```Markdown
![some text](file.png)
```

That's all there is to it
