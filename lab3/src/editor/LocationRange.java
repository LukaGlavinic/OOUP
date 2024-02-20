package editor;

public class LocationRange {
 
    private final Location start;
    private final Location finish;

    public LocationRange(Location start, Location finish) {
        this.start = start;
        this.finish = finish;
    }

    public Location getStart() {
        return start;
    }

    public Location getFinish() {
        return finish;
    }

    public void setStart(Location start) {
		this.start.setX(start.getX());
		this.start.setY(start.getY());
	}

	public void setFinish(Location finish) {
		this.finish.setX(finish.getX());
		this.finish.setY(finish.getY());
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((start == null) ? 0 : start.hashCode());
        result = prime * result + ((finish == null) ? 0 : finish.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        LocationRange other = (LocationRange)obj;
        if(start == null) {
            if(other.start != null)
                return false;
        }else if(!start.equals(other.start))
            return false;
        if(finish == null) {
            return other.finish == null;
        }else return finish.equals(other.finish);
    }
}