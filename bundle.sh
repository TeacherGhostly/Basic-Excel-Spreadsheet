#!/bin/bash

if [ -f submission.zip ]; then
  old=$(date +%s)
  echo "Backup up old submission as submission-${old}"
  echo
  mv submission.zip submission-${old}.zip
fi

zip submission.zip src/sheep/expression/ExpressionFactory.java
zip submission.zip src/sheep/expression/InvalidExpression.java
zip submission.zip src/sheep/expression/Expression.java
zip submission.zip src/sheep/expression/TypeError.java
zip submission.zip src/sheep/expression/CoreFactory.java

zip submission.zip src/sheep/expression/basic/Reference.java
zip submission.zip src/sheep/expression/basic/Constant.java
zip submission.zip src/sheep/expression/basic/Nothing.java

zip submission.zip src/sheep/expression/arithmetic/Arithmetic.java
zip submission.zip src/sheep/expression/arithmetic/Equal.java
zip submission.zip src/sheep/expression/arithmetic/Divide.java
zip submission.zip src/sheep/expression/arithmetic/Less.java
zip submission.zip src/sheep/expression/arithmetic/Plus.java
zip submission.zip src/sheep/expression/arithmetic/Minus.java
zip submission.zip src/sheep/expression/arithmetic/Times.java

zip submission.zip src/sheep/sheets/DisplaySheet.java
zip submission.zip src/sheep/sheets/CellLocation.java
zip submission.zip src/sheep/sheets/Sheet.java
zip submission.zip src/sheep/sheets/FixedSheet.java
zip submission.zip src/sheep/sheets/SheetBuilder.java

zip submission.zip src/sheep/parsing/Parser.java
zip submission.zip src/sheep/parsing/ParseException.java
zip submission.zip src/sheep/parsing/SimpleParser.java

zip submission.zip test/sheep/sheets/CellLocationTest.java
zip submission.zip test/sheep/expression/basic/ReferenceTest.java

zip -r submission.zip ai/*
