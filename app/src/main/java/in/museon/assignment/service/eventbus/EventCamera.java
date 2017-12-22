package in.museon.assignment.service.eventbus;


public class EventCamera {
   private int requestCamera=0;
    public EventCamera(int requestCamera) {
        this.requestCamera=requestCamera;
    }

    public int getRequestCamera() {
        return this.requestCamera;
    }
}
