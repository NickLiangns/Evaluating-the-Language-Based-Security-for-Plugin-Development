package NetworkManager;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ListHttpRequestsAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent event) {
        Project project = event.getProject();
        if (project != null) {
            List<InetSocketAddress> connections = new ArrayList<>();
            try {
                InetAddress[] addresses = InetAddress.getAllByName("localhost");
                for (InetAddress address : addresses) {
                    try (Socket socket = new Socket()) {
                        socket.connect(new InetSocketAddress(address, 8080), 1000);
                        connections.add(new InetSocketAddress(address, 8080));
                    }
                }
                if (connections.isEmpty()) {
                    Messages.showInfoMessage("No HTTP connections found on this machine", "Information");
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (InetSocketAddress connection : connections) {
                        sb.append(connection.getHostString()).append(":").append(connection.getPort()).append("\n");
                    }
                    Messages.showInfoMessage(sb.toString(), "HTTP Connections");
                }
            } catch (IOException e) {
                e.printStackTrace();
                Messages.showErrorDialog("Error while listing HTTP connections: " + e.getMessage(), "Error");
            }
        }
    }
}

