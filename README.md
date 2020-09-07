# Constraint Satisfaction Problem. (Wroclaw, 2017)

### Artificial Intelligence Lab, by Prof. [Maciej Piasecki](https://www.linkedin.com/in/maciej-piasecki-020b657/)

There is a board of size *n* x *m* with fields to be used as a canvas for a crossword.

A set *S* of all English words (or for any other natural language) can be used to select words from, but only *k* of them can be used in a crossword.

The problem parameters are: *S*, *n*, *m* and *k*. We need to find such a way of placing words across the board that one field includes at most one letter and every continuous sequence of letters read horizontally or vertically is a word from the selected *S* subset of size *k*.

A solution must include exactly *k* words from *S*. Some fields of the board can be left empty.

*S* is to be acquired in the following way:
1. download the [list of english lemmas](https://www.kilgarriff.co.uk/bnc-readme.html) extracted from the British National Corpus prepared by Prof. Adam Kilgarriff
2. select from the list only: adverbs (adv), verbs (v), adjectives (a) and nouns (n)

For more info see:

+ [**Task Definition.pdf**](https://github.com/misrraimsp/crossword/blob/master/Task%20Definition.pdf)
+ [**Final Report.pdf**](https://github.com/misrraimsp/crossword/blob/master/Final%20Report.pdf)
