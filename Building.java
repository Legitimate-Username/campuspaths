package hw7;

public class Building{

	private String name;
	private Integer id;
	private Integer x;
	private Integer y;

	public Building(){
		
	}

	public Building(String n, Integer i, Integer a, Integer b){
		name=n;
		id=i;
		x=a;
		y=b;
	}

	public Building(Building b){
		name=b.getName();
		id=b.getID();
		x=b.getX();
		y=b.getY();
	}

	public String getName(){
		return name;
	}

	public Integer getID(){
		return id;
	}

	public Integer getX(){
		return x;
	}
	
	public Integer getY(){
		return y;
	}
	
	public void setName(String n){
		name=n;
	}
	
	public void setID(Integer i){
		id=i;
	}
	
	public void setX(Integer a){
		x=a;
	}
	
	public void setY(Integer b){
		y=b;
	}

	@Override
	public boolean equals(Object obj){
		//if(obj instanceof Node<?, ?>){
			Building b=(Building) obj;
			if(this.getID()==b.getID()){
				return true;
			}
		//}
		return false;
		//return super.equals(obj);
	}
	
	@Override
	public int hashCode()
	{
	    return id.hashCode();
	}
}