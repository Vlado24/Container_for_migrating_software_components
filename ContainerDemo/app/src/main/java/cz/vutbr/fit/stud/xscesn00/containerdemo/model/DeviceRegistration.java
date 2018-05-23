package cz.vutbr.fit.stud.xscesn00.containerdemo.model;

public class DeviceRegistration {

  private String name;
  private String registration_id;
  private String device_id;
  private boolean active;
  private String type;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRegistration_id() {
    return registration_id;
  }

  public void setRegistration_id(String registration_id) {
    this.registration_id = registration_id;
  }

  public String getDevice_id() {
    return device_id;
  }

  public void setDevice_id(String device_id) {
    this.device_id = device_id;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
