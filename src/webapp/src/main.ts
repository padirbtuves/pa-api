import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { AppModule } from './app/app.module';
import './css/styles.css'

const platform = platformBrowserDynamic();
platform.bootstrapModule(AppModule);