# JINQ

**J**ava **IN**tegrated **Q**uery in parlance with LINQ is an _ultra minimalistic_ library for Java inspired from and mimicking the .NET [LINQ](http://msdn.microsoft.com/en-us/netframework/aa904594.aspx). While LINQ is a language construct, JINQ is composed of types - classes and methods, but to the same effect.

JINQ allows you to rewrite conventional and nested loop based data processing code into query oriented and highly readable code.

Here is a typical loop based data processing code:

```java
List<ProductDisplayInfo> getDisplayList(Product[] products) {
	List<ProductDisplayInfo> pdiList = new ArrayList<>();

	for (int index = 0; index < products.length; ++index) {
		Product p = products[index];

		if (product.getCategory() == 2 || product.getCategory() == 5) {
			boolean yes = isProductUnderDiscount(product);

			if (!yes) {
				ProductDisplayInfo pdi = new ProductDisplayInfo();
				// whatever you want to populate pdi with ...

				pdiList.add(pdi);
			}
		}
	}

	return pdiList;
}
```
Here is a C# LINQ snippet:

```csharp
var query = from p in products
	where p.getCategory == 2 || p.getCategory == 5 && isProductUnderDiscount(p)
	select new ProductDisplayInfo(p);

var pdiList = query.ToList();
```

Here is JINQ in action:

```java
final Iterable<ProductDisplayInfo> query = new Enumerable<>(products)
	.where(p -> p.getCategory == 2 || p.getCategory == 5 && isProductUnderDiscount(p))
	.select(p -> { new ProductDisplayInfo(p); } ); // or select(ProductDisplayInfo::new);

final List<ProductDisplayInfo> pdiList = query.toList();
```

or

```java
for (ProductDisplayInfo pdi : pdiList) {
	System.out.println(pdi);
}
```

Elaboration of JINQ is in progress. Until then you can try the related blog [post](http://vivekragunathan.wordpress.com).