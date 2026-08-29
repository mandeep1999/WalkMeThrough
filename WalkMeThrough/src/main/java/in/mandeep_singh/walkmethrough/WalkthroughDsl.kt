package `in`.mandeep_singh.walkmethrough

/**
 * Marks walkthrough builder scopes to prevent accidental nesting across DSL boundaries.
 *
 * @see WalkthroughBuilder
 */
@DslMarker
annotation class WalkthroughDsl
