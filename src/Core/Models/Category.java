package Core.Models;

public class Category implements Comparable<Category> {
	public String CategoryName;
	public Category(String name)
	{
		CategoryName = name;
	}
	
	@Override
	public int compareTo(Category comparedCat) {
		if(CategoryName == comparedCat.CategoryName)
			return 0;
		else if(CategoryName.charAt(0) < comparedCat.CategoryName.charAt(0))
		{
			return -1;
		}
		else
			return 1;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (obj == null) {
	        return false;
	    }
	    if (!Category.class.isAssignableFrom(obj.getClass())) {
	        return false;
	    }
	    final Category other = (Category) obj;
	    if ((this.CategoryName == null) ? (other.CategoryName != null) : !this.CategoryName.equals(other.CategoryName)) {
	        return false;
	    }
	    return true;
	}
}
