/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */

import { NewAppScreen } from '@react-native/new-app-screen';
import { StatusBar, StyleSheet, useColorScheme, View } from 'react-native';
import {
  SafeAreaProvider,
  useSafeAreaInsets,
} from 'react-native-safe-area-context';

import { useState } from 'react';
import { Button, Text, View } from 'react-native';
import { NativeModules } from 'react-native';

const { STTModule } = NativeModules;

export default function App() {
  const [text, setText] = useState("");

  const start = async () => {
    await STTModule.startRecording();
  };

  const stop = async () => {
    await STTModule.stopRecording();
    const result = await STTModule.transcribe();
    setText(result);
  };

  return (
    <View>
      <Button title="Start Recording" onPress={start} />
      <Button title="Stop & Transcribe" onPress={stop} />
      <Text>{text}</Text>
    </View>
  );
}

function App() {
  const isDarkMode = useColorScheme() === 'dark';

  return (
    <SafeAreaProvider>
      <StatusBar barStyle={isDarkMode ? 'light-content' : 'dark-content'} />
      <AppContent />
    </SafeAreaProvider>
  );
}

function AppContent() {
  const safeAreaInsets = useSafeAreaInsets();

  return (
    <View style={styles.container}>
      <NewAppScreen
        templateFileName="App.tsx"
        safeAreaInsets={safeAreaInsets}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});

export default App;
