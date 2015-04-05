package cz.wa2.poll.frontend.dto;


public class VoterGroupDTO {

    private Long id;
    private String name;
    private String description;
    private VoterDTO supervisor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public VoterDTO getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(VoterDTO supervisor) {
        this.supervisor = supervisor;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Name: "+name+"\n");
        sb.append("Description: "+description+"\n");
        return sb.toString();
    }
}
