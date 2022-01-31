package home.developer.service.impl;

import home.developer.service.MouseService;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public class MouseServiceImpl implements MouseService {

    @Override
    public Point getCurrentPosition() {
        return MouseInfo.getPointerInfo().getLocation();
    }
}
