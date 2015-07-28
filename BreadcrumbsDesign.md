# Introduction #

This page is not meant to be read by GWTP user, unless you want to get a glimpse of the design process for an upcoming feature. Here, we should lay down the basic design of the breadcrumb features that we consider adding to GWTP.

# Goals #

  * Each page should be able to sit within a hierarchy of presenters that logically display which presenter comes _logically_ before the current one
  * This hierarchy should be easy to display with meaningful page names, i.e. "Main > Settings > Friends", elements in this display can be clicked to navigate back to parent presenters.
  * It should be possible to possess both "breadcrumbed" and "non-breadcrumbed" pages in the same application.
  * The logical hierarchy can be different from the nested presenter hierarchy
  * Bookmarking a presenter deep in the logical hierarchy should work
  * Instantiating a presenter deep in the logical hierarchy should not force us to instantiate all the parent presenters.

# Non-goals #
  * This should not duplicate the browser history. A presenter fits a very precise place within the logical hierarchy.


# Details #

How do we do this? For now, let's discuss it in the forum