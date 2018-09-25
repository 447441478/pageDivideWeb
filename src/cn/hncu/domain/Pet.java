package cn.hncu.domain;
/*
 建表语句
CREATE TABLE pet(
	id INT PRIMARY KEY AUTO_INCREMENT,
	NAME VARCHAR(20),
	color VARCHAR(10)
); 
 */
public class Pet {
	private Integer id; //主键
	
	private String name; //宠物名
	
	private String color; //宠物颜色

	public Pet() {
	}
	
	@Override
	public String toString() {
		return "Pet [id=" + id + ", name=" + name + ", color=" + color + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pet other = (Pet) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
