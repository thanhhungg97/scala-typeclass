### Working with type class in scala is working with implicit values and implicit parameters.
#### Packaging implicits
We must package any definitions marked implicit in side an object or trait rather than at the top level scope
#### Implicit Scope
The compiler searches for candidate type class instance by type.
The compiler searches for candidate instances in the implicit scope at the call site:
1. local or inherited definitions
2. imported definitions
3. definitions im the companion object of the type class or the parameter type(in this case JsonWriter or Person)

Then we can package type class instances in four ways:
1. by placing them in an object => we bring instances into scope by importing them
2. by placing them in a trait => ... inheritance
3. by placing them in the companion object of the type class => instances are always in implicit scope.
4. by placing them in companion object of the parameter type => instances are always in implicit scope.
#### Recursive Implicit Resolution
