# sudoku

### Testing Strategy.
Sudoku boards are traditionally rank 3.  That means there are 3^2 rows
and 3^2 columns and 3^2 possible digits.  

We will begin testing with a rank 1 board.  Such a board has one row,
one column, and only one digit.  Thus there is only one solution.  `[[1]]`.  

Then we'll move on to the rank 2 board.  This has 4 rows, 4 columns, and
4 digits.  They have the form:

    [[d d d d]
    [d d d d]
    [d d d d]
    [d d d d]]

Once that is solved, I expect the general case to be solved as well.
  
